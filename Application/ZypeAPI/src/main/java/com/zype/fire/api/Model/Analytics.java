package com.zype.fire.api.Model;

import com.google.gson.annotations.Expose;

/**
 * Created by Evgeny Cherkasov on 13.02.2018.
 */

public class Analytics {
    @Expose
    public String beacon;

    @Expose
    public AnalyticsDimensions dimensions;
}
