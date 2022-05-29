import React, {useEffect, useState} from "react";
import {apiGetMyRestaurants} from "./Admin/Restaurant/Actions";
import {RestaurantList} from "../components/Restaurant/RestaurantList";

const Restaurants = () =>{
    let role = localStorage.getItem("role");

    if(role==="Admin"){
        return
    }else{
        return
    }
    let [data,setData] = useState(null);
    let [loading, setLoading] = useState(false);
    let email = localStorage.getItem("email");

    useEffect(()=>{
        setLoading(true);
        apiGetMyRestaurants(email,(data)=>{
            setData(data);
            setLoading(false);
        },(error)=>{
            setData(null);
            setLoading(false);
        })
    },[])

    if(loading) return <>Načítám restaurace</>
    if(!data) return <>Žádné restaurace nenalezeny</>
    if(data) return <RestaurantList restaurants={data}/>
}

export default Restaurants;