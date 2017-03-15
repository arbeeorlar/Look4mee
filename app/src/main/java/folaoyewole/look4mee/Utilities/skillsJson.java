package folaoyewole.look4mee.Utilities;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import folaoyewole.look4mee.Model.Skill;

/**
 * Created by sp_developer on 11/2/16.
 */
public class skillsJson {

    private JSONObject jsonObject;
    private JSONArray jsonArray;

    public JSONArray toJSON(ArrayList<Skill> string_value){

        try{

            jsonArray = new JSONArray();

            for(int i = 0 ; i < string_value.size(); i++ ){

                jsonObject = new JSONObject();


                jsonObject.put("Id", string_value.get(i).getId());
                jsonObject.put("SkillName", string_value.get(i).getSkillName());

                jsonArray.put(jsonObject);

            }

            return jsonArray;

        } catch (Exception e){
            e.printStackTrace();

            jsonArray = null;
        }

        return jsonArray;
    }

}
