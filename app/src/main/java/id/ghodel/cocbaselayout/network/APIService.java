package id.ghodel.cocbaselayout.network;

import id.ghodel.cocbaselayout.model.BaseModel;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Muhammad Mayudin on 03-Jul-21.
 */
public interface APIService {

    @GET("get_data/")
    Call<BaseModel> getCocBase();
}
