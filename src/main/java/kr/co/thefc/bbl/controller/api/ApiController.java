package kr.co.thefc.bbl.controller.api;

import io.swagger.annotations.ApiParam;
import kr.co.thefc.bbl.service.DBConnService;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/api")
public class ApiController {
    @Autowired
    private DBConnService dbConnService;

    @RequestMapping(value="/now", method = RequestMethod.POST)
    public HashMap now() {
        log.info("####now#####");
        HashMap rtnVal = new HashMap();

        List<HashMap> list = dbConnService.select("select_now_api", null);
        rtnVal.put("now", list.get(0).get("now"));

        return rtnVal;
    }

    @RequestMapping(value="/getPTLessionVouchars", method = RequestMethod.POST)
    public HashMap getPTLessionVouchars(@RequestBody String data) {
        log.info("####getPTLessionVouchars##### : " + data);
        HashMap rtnVal = new HashMap();

        JSONParser parser = new JSONParser();
        String error = null;

        try {
            JSONObject jsonData = (JSONObject) parser.parse(data);

            HashMap map = new HashMap();
            Set set = jsonData.keySet();
            jsonData.forEach((key, value) -> map.put(key,value));

            List<HashMap> list = dbConnService.select("getPTLessionVouchars", map);
            HashMap infos = new HashMap();
            infos.put("products", list);
            for (Object key:map.keySet()) {
                infos.put(key,map.get(key));
            }
            rtnVal.put("infos", infos);

        } catch (ParseException e) {
            e.printStackTrace();
            error = "정보를 파싱하지 못했습니다.";
        }

        if (error!=null) {
            rtnVal.put("result", false);
        }
        else {
            rtnVal.put("result", true);
        }
        rtnVal.put("errorMsg", error);

        return rtnVal;
    }

    @RequestMapping(value="/getPTLessionVoucharDetail", method = RequestMethod.POST)
    public HashMap getPTLessionVoucharDetail(@RequestBody String data) {
        log.info("####getPTLessionVoucharDetail##### : " + data);
        HashMap rtnVal = new HashMap();

        JSONParser parser = new JSONParser();
        String error = null;

        try{
            JSONObject jsonData = (JSONObject) parser.parse(data);

            // 넘어온 data가 있는지 확인
            if(jsonData.isEmpty()) {
                error = "Data is empty";
            } else {
                HashMap map = new HashMap();
                Set set = jsonData.keySet();
                jsonData.forEach((key, value) -> map.put(key,value));

                List<HashMap> list = dbConnService.select("getPTLessionVoucharsDetail", map);

                // 검색해서 가져온 list에 값이 들어있는지 확인
                if(list.isEmpty()) {
                    error = "Product index " +jsonData.values() + " not found";
                } else {
                    HashMap infos = new HashMap();
                    infos.put("productInfo", list);
                    rtnVal.put("infos", infos);
                }
            }

        } catch (ParseException e) {
            e.printStackTrace();
            error = "정보를 파싱하지 못했습니다.";
        }

        if (error!=null) {
            rtnVal.put("result", false);
        }
        else {
            rtnVal.put("result", true);
        }
        rtnVal.put("errorMsg", error);

        return rtnVal;
    }

    @RequestMapping(value="/getPTTrainers", method = RequestMethod.POST)
    public HashMap getPTTrainers(@RequestBody String data) {
        log.info("####getPTTrainers##### : " + data);
        HashMap rtnVal = new HashMap();

        JSONParser parser = new JSONParser();
        String error = null;

        try{
            JSONObject jsonData = (JSONObject) parser.parse(data);

            HashMap map = new HashMap();
            Set set = jsonData.keySet();
            jsonData.forEach((key, value) -> map.put(key,value));

            List<HashMap> list = dbConnService.select("getPTTrainers", map);
            HashMap infos = new HashMap();
            infos.put("PTTrainers", list);

            rtnVal.put("infos", infos);

        } catch (ParseException e) {
            e.printStackTrace();
            error = "정보를 파싱하지 못했습니다.";
        }

        if (error!=null) {
            rtnVal.put("result", false);
        }
        else {
            rtnVal.put("result", true);
        }
        rtnVal.put("errorMsg", error);

        return rtnVal;
    }

    @RequestMapping(value="/getPTTrainerDetail", method = RequestMethod.POST)
    public HashMap getPTTrainerDetail(@RequestBody String data) {
        log.info("####getPTTrainerDetail##### : " + data);
        HashMap rtnVal = new HashMap();

        JSONParser parser = new JSONParser();
        String error = null;

        try{
            JSONObject jsonData = (JSONObject) parser.parse(data);

            if(jsonData.isEmpty()) {
                error = "Data is empty";
            } else {
                HashMap map = new HashMap();
                Set set = jsonData.keySet();
                jsonData.forEach((key, value) -> map.put(key, value));

                List<HashMap> list = dbConnService.select("getPTTrainerDetail", map);

                if (list.isEmpty()) {
                    error = "Trainer index " +jsonData.values() + " not found";
                } else {
                    HashMap infos = new HashMap();
                    infos.put("PTTrainerInfo", list);

                    list = dbConnService.select("getPTTrainerDetail_workExperience", map);
                    infos.put("workExperience", list);

                    list = dbConnService.select("getPTTrainerDetail_awardWinning", map);
                    infos.put("awardWinning", list);

                    list = dbConnService.select("getPTTrainerDetail_qualification", map);
                    infos.put("qualification", list);

                    rtnVal.put("infos", infos);
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
            error = "정보를 파싱하지 못했습니다.";
        }

        if (error!=null) {
            rtnVal.put("result", false);
        }
        else {
            rtnVal.put("result", true);
        }
        rtnVal.put("errorMsg", error);

        return rtnVal;
    }

    @RequestMapping(value="/getShoppingItems", method = RequestMethod.POST)
    public HashMap getShoppingItems(@RequestBody String data) {
        log.info("####getShoppingItems##### : " + data);
        HashMap rtnVal = new HashMap();

        JSONParser parser = new JSONParser();
        String error = null;

        try{
            JSONObject jsonData = (JSONObject) parser.parse(data);

            HashMap map = new HashMap();
            Set set = jsonData.keySet();
            jsonData.forEach((key, value) -> map.put(key,value));

            List<HashMap> list = dbConnService.select("getShoppingItems", map);

            HashMap infos = new HashMap();
            infos.put("usersShoppingBasketProducts", list);


            rtnVal.put("infos", infos);
        } catch (ParseException e) {
            e.printStackTrace();
            error = "정보를 파싱하지 못했습니다.";
        }

        if (error!=null) {
            rtnVal.put("result", false);
        }
        else {
            rtnVal.put("result", true);
        }
        rtnVal.put("errorMsg", error);

        return rtnVal;
    }

    @RequestMapping(value="/addShoppingItems", method = RequestMethod.POST)
    public HashMap addShoppingItems(@RequestBody String data) {
        log.info("####addShoppingItems##### : " + data);
        HashMap rtnVal = new HashMap();

        JSONParser parser = new JSONParser();
        String error = null;

        try{
            JSONObject jsonData = (JSONObject) parser.parse(data);

            HashMap map = new HashMap();
            Set set = jsonData.keySet();
            jsonData.forEach((key, value) -> map.put(key,value));

            // 에러 추가
            // 1. productIdx가 없음
            // 2. userIdx가 없음
            // 3. quantity가 없음
            
            int result = dbConnService.insert("addShoppingItems", map);

            if(result == 0) {
                error = "Failed to add to shopping basket";
            }
        } catch (ParseException e) {
            e.printStackTrace();
            error = "정보를 파싱하지 못했습니다.";
        }

        if (error!=null) {
            rtnVal.put("result", false);
        }
        else {
            rtnVal.put("result", true);
        }
        rtnVal.put("errorMsg", error);

        return rtnVal;
    }

    @RequestMapping(value="/deleteShoppingItems", method = RequestMethod.POST)
    public HashMap deleteShoppingItems(@RequestBody String data) {
        log.info("####deleteShoppingItems##### : " + data);
        HashMap rtnVal = new HashMap();

        JSONParser parser = new JSONParser();
        String error = null;

        try{
            JSONObject jsonData = (JSONObject) parser.parse(data);

            HashMap map = new HashMap();
            Set set = jsonData.keySet();
            jsonData.forEach((key, value) -> map.put(key,value));

            int result = dbConnService.delete("deleteShoppingItems", map);

            if(result == 0) {
                error = "Failed to delete from shopping basket";
            }
        } catch (ParseException e) {
            e.printStackTrace();
            error = "정보를 파싱하지 못했습니다.";
        }

        if (error!=null) {
            rtnVal.put("result", false);
        }
        else {
            rtnVal.put("result", true);
        }
        rtnVal.put("errorMsg", error);

        return rtnVal;
    }

    // 찜
    @RequestMapping(value="/getUserPick", method = RequestMethod.POST)
    public HashMap getUserPick(@RequestBody String data) {
        log.info("####getUserPick##### : " + data);
        HashMap rtnVal = new HashMap();

        JSONParser parser = new JSONParser();
        String error = null;

        try{
            JSONObject jsonData = (JSONObject) parser.parse(data);

            HashMap map = new HashMap();
            Set set = jsonData.keySet();
            jsonData.forEach((key, value) -> map.put(key,value));

            List<HashMap> list = dbConnService.select("getUserPick", map);

            // productInfo || PTTrainer || store
            HashMap infos = new HashMap();
            infos.put("", list);

            rtnVal.put("infos", infos);
        } catch (ParseException e) {
            e.printStackTrace();
            error = "정보를 파싱하지 못했습니다.";
        }

        if (error!=null) {
            rtnVal.put("result", false);
        }
        else {
            rtnVal.put("result", true);
        }
        rtnVal.put("errorMsg", error);

        return rtnVal;
    }

    @RequestMapping(value="/getUserPickDetail", method = RequestMethod.POST)
    public HashMap getUserPickDetail(@RequestBody String data) {
        log.info("####getUserPickDetail##### : " + data);
        HashMap rtnVal = new HashMap();

        JSONParser parser = new JSONParser();
        String error = null;

        try{
            JSONObject jsonData = (JSONObject) parser.parse(data);

            HashMap map = new HashMap();
            Set set = jsonData.keySet();
            jsonData.forEach((key, value) -> map.put(key,value));

            List<HashMap> list = dbConnService.select("getUserPickDetail", map);

            // productInfo || PTTrainer || store
            HashMap infos = new HashMap();
            infos.put("", list);

            rtnVal.put("infos", infos);
        } catch (ParseException e) {
            e.printStackTrace();
            error = "정보를 파싱하지 못했습니다.";
        }

        if (error!=null) {
            rtnVal.put("result", false);
        }
        else {
            rtnVal.put("result", true);
        }
        rtnVal.put("errorMsg", error);

        return rtnVal;
    }

    @RequestMapping(value="/addUserPick", method = RequestMethod.POST)
    public HashMap addUserPick(@RequestBody String data) {
        log.info("####addUserPick##### : " + data);
        HashMap rtnVal = new HashMap();

        JSONParser parser = new JSONParser();
        String error = null;

        try{
            JSONObject jsonData = (JSONObject) parser.parse(data);

            HashMap map = new HashMap();
            Set set = jsonData.keySet();
            jsonData.forEach((key, value) -> map.put(key,value));

            int result = dbConnService.insert("addUserPick", map);

            if(result == 0) {
                error = "Failed to add to user pick list";
            }

        } catch (ParseException e) {
            e.printStackTrace();
            error = "정보를 파싱하지 못했습니다.";
        }

        if (error!=null) {
            rtnVal.put("result", false);
        }
        else {
            rtnVal.put("result", true);
        }
        rtnVal.put("errorMsg", error);

        return rtnVal;
    }

    @RequestMapping(value="/deleteUserPick", method = RequestMethod.POST)
    public HashMap deleteUserPick(@RequestBody String data) {
        log.info("####deleteUserPick##### : " + data);
        HashMap rtnVal = new HashMap();

        JSONParser parser = new JSONParser();
        String error = null;

        try{
            JSONObject jsonData = (JSONObject) parser.parse(data);

            HashMap map = new HashMap();
            Set set = jsonData.keySet();
            jsonData.forEach((key, value) -> map.put(key,value));

            int result = dbConnService.delete("deleteUserPick", map);

            if(result == 0) {
                error = "Failed to delete from user pick list";
            }
        } catch (ParseException e) {
            e.printStackTrace();
            error = "정보를 파싱하지 못했습니다.";
        }

        if (error!=null) {
            rtnVal.put("result", false);
        }
        else {
            rtnVal.put("result", true);
        }
        rtnVal.put("errorMsg", error);

        return rtnVal;
    }

    @RequestMapping(value="/buyProduct", method = RequestMethod.POST)
    public HashMap buyProduct(@RequestBody String data) {
        log.info("####buyProduct##### : " + data);
        HashMap rtnVal = new HashMap();

        JSONParser parser = new JSONParser();
        String error = null;

        try{
            JSONObject jsonData = (JSONObject) parser.parse(data);

            HashMap map = new HashMap();
            Set set = jsonData.keySet();
            jsonData.forEach((key, value) -> map.put(key,value));

        } catch (ParseException e) {
            e.printStackTrace();
            error = "정보를 파싱하지 못했습니다.";
        }

        if (error!=null) {
            rtnVal.put("result", false);
        }
        else {
            rtnVal.put("result", true);
        }
        rtnVal.put("errorMsg", error);

        return rtnVal;
    }

    @RequestMapping(value="/getTransactions", method = RequestMethod.POST)
    public HashMap getTransactions(@RequestBody String data) {
        log.info("####getTransactions##### : " + data);
        HashMap rtnVal = new HashMap();

        JSONParser parser = new JSONParser();
        String error = null;

        try{
            JSONObject jsonData = (JSONObject) parser.parse(data);

            HashMap map = new HashMap();
            Set set = jsonData.keySet();
            jsonData.forEach((key, value) -> map.put(key,value));

        } catch (ParseException e) {
            e.printStackTrace();
            error = "정보를 파싱하지 못했습니다.";
        }

        if (error!=null) {
            rtnVal.put("result", false);
        }
        else {
            rtnVal.put("result", true);
        }
        rtnVal.put("errorMsg", error);

        return rtnVal;
    }

    @RequestMapping(value="/cancelTransaction", method = RequestMethod.POST)
    public HashMap cancelTransaction(@RequestBody String data) {
        log.info("####cancelTransaction##### : " + data);
        HashMap rtnVal = new HashMap();

        JSONParser parser = new JSONParser();
        String error = null;

        try{
            JSONObject jsonData = (JSONObject) parser.parse(data);

            HashMap map = new HashMap();
            Set set = jsonData.keySet();
            jsonData.forEach((key, value) -> map.put(key,value));

        } catch (ParseException e) {
            e.printStackTrace();
            error = "정보를 파싱하지 못했습니다.";
        }

        if (error!=null) {
            rtnVal.put("result", false);
        }
        else {
            rtnVal.put("result", true);
        }
        rtnVal.put("errorMsg", error);

        return rtnVal;
    }
}
