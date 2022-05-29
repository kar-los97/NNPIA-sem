import React from "react";
import {axios} from "../../axiosConfig";

const url = "api/v1/user"

export const apiLoginUser=(values,callback,error)=>{
    axios.post(url+"/login",values)
        .then(response=>callback(response.data))
        .catch(err=>error(err));
}

export const apiAuthorizeUser=(callback,error)=>{
    axios.post(url+"/authorize")
        .then(response=>callback(response.data))
        .catch(err=>error(err));
}