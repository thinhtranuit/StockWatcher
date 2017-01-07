package com.thinhtranuit.example.stockWatcher.client;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

/**
 * Created by thinh on 07/01/2017.
 */
public interface MyResource extends ClientBundle {
    @ClientBundle.Source("../shared/google_code.jpg")
    ImageResource logo();
}
