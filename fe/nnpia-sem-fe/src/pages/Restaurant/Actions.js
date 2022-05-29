import React from "react";

import {axios} from "../../../axiosConfig";

const url = "/api/v1/restaurant"

export const apiGetMyRestaurants=(email,callback,error)=>{
    axios.get(url+"/user/"+email)
        .then(response=>callback(response.data))
        .catch(err=>error(err));
}

export const apiGetAllRestaurants=(callback,error)=>{}

export const apiGetRestaurantById=(id,callback,error)=>{
    axios.get(url+"/"+id)
        .then(response=>callback(response.data))
        .catch(err=>error(err));
}

export const apiCreateRestaurant =(values,callback,error)=>{
    axios.post(url)
        .then(response=>callback(response.data))
        .catch(err=>error(err));
}