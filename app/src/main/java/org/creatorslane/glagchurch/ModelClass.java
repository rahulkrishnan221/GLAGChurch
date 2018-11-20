package org.creatorslane.glagchurch;

/**
 * Created by rahul on 17/8/18.
 */

public class ModelClass {

    String title,image;

    public ModelClass(String image,String title)
    {
        this.image=image;
        this.title=title;
    }
    public ModelClass()
    {

    }
    public String getTitle() { return title; }
    public void setTitle(String title)
    {
        this.title=title;
    }
    public String getImage()
    {
        return image;
    }
    public void setImage(String image)
    {
        this.image=image;
    }
}
