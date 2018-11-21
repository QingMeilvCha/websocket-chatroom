package com.shein.comtroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by dell on 2018/11/19.
 */
@Controller
public class Client_Entrance {


    @GetMapping(value = "chat")
    public String test(){
        return "index";
    }

}
