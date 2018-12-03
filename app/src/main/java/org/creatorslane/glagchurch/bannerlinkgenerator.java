package org.creatorslane.glagchurch;

/**
 * Created by rahul on 3/12/18.
 */

public class bannerlinkgenerator {
    String BasePath = "http://workshop.creatorslane.org/Wesley/Books/";
   public String[] generator(String BannerLoc,int count)
    {
        String arr[]=new String[count];
       BasePath =BasePath+BannerLoc+"/";
       for(int i=0;i<count;i++)
       {
         arr[i]=BasePath+(i+1)+".jpg";
       }

        return arr;
    }

}
