package com.dcasado.defender.model.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by davidcasado on 3/2/16.
 */
public class BoundsComponent implements Component{
    public final Rectangle bounds = new Rectangle();
}
