package id.ghodel.cocbaselayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import id.ghodel.cocbaselayout.adapter.CocAdapter;
import id.ghodel.cocbaselayout.model.BaseModel;
import id.ghodel.cocbaselayout.network.APIService;
import id.ghodel.cocbaselayout.network.CocBaseTask;
import id.ghodel.cocbaselayout.network.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity  {

    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler_data);

        APIService apiService = RetrofitClient.createService(APIService.class);
        Call<BaseModel> call = apiService.getCocBase();

        call.enqueue(new Callback<BaseModel>() {
            @Override
            public void onResponse(Call<BaseModel> call, Response<BaseModel> response) {
                CocAdapter cocAdapter = new CocAdapter(MainActivity.this, response.body().getWar());
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                recyclerView.setAdapter(cocAdapter);
                cocAdapter.notifyDataSetChanged();

                Log.i("Result", response.body().toString());
            }

            @Override
            public void onFailure(Call<BaseModel> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Gagal Koneksi Ke Server", Toast.LENGTH_SHORT).show();
            }
        });


    }

}