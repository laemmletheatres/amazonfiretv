package com.zype.fire.api.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Evgeny Cherkasov on 27.03.2019.
 */

public class ZobjectTopPlaylistResponse {
    @SerializedName("response")
    @Expose
    public List<ZobjectTopPlaylist> zobjectContents = new ArrayList<>();

}
