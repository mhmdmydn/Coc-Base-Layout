package id.ghodel.cocbaselayout.fragment.townhall;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.tfb.fbtoast.FBToast;

import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;
import id.ghodel.cocbaselayout.BuildConfig;
import id.ghodel.cocbaselayout.R;
import id.ghodel.cocbaselayout.adapter.townhall.TrophyAdapter;
import id.ghodel.cocbaselayout.adapter.townhall.WarAdapter;
import id.ghodel.cocbaselayout.fragment.BaseFragment;
import id.ghodel.cocbaselayout.model.townhall.BaseModel;
import id.ghodel.cocbaselayout.network.APIService;
import id.ghodel.cocbaselayout.network.RetrofitClient;
import id.ghodel.cocbaselayout.utils.MainUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TrophyFragment extends BaseFragment{

    private RecyclerView recyclerView;
    private SmoothProgressBar smoothProgressBar;
    private APIService trophyApiService;
    private GridLayoutManager gridLayoutManager;
    private TrophyAdapter trophyAdapter;

    public TrophyFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trophy, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
        initLogic(view);
        initListener(view);
    }

    @Override
    public void initView(View view) {
        recyclerView = view.findViewById(R.id.recycler_data);
        smoothProgressBar =  view.findViewById(R.id.google_now);
    }

    @Override
    public void initLogic(View view) {

    }

    @Override
    public void initListener(View view) {

    }

    private void loadData(){
        if (MainUtils.isNetworkConnected(getActivity())) {
            smoothProgressBar.progressiveStart();
            recyclerView.setVisibility(View.INVISIBLE);

            RetrofitClient retrofitClient = new RetrofitClient();

            trophyApiService = retrofitClient.getClient(BuildConfig.BASE_URL).create(APIService.class);
            Call<BaseModel> call = trophyApiService.getCocBase();

            call.enqueue(new Callback<BaseModel>() {
                @Override
                public void onResponse(Call<BaseModel> call, Response<BaseModel> response) {
                    FBToast.successToast(getActivity(), "Sukses konek ke server", FBToast.LENGTH_SHORT);

                    if(response.isSuccessful() && response.body() != null){
                        smoothProgressBar.progressiveStop();
                        recyclerView.setVisibility(View.VISIBLE);
                        trophyAdapter = new TrophyAdapter(getActivity(), response.body().getThropy());
                        recyclerView.setHasFixedSize(true);
                        gridLayoutManager = new GridLayoutManager(getActivity(), 2);
                        recyclerView.setLayoutManager(gridLayoutManager);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAdapter(trophyAdapter);
                        trophyAdapter.notifyDataSetChanged();

                        Log.i("Result", response.body().toString());
                    }
                }

                @Override
                public void onFailure(Call<BaseModel> call, Throwable t) {
                    recyclerView.setVisibility(View.INVISIBLE);

                    smoothProgressBar.progressiveStart();
                    FBToast.errorToast(getActivity(), "Gagal Koneksi Ke Server", FBToast.LENGTH_SHORT);
                }
            });
        } else {
            recyclerView.setVisibility(View.INVISIBLE);

            smoothProgressBar.progressiveStart();
            FBToast.errorToast(getActivity(), "Tidak ada koneksi internet", FBToast.LENGTH_SHORT);

        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText != null){
                    trophyAdapter.getFilter().filter(newText);
                }
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }


}