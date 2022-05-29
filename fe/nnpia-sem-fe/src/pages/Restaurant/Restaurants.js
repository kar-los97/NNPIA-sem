import React, {useEffect, useState} from "react";
import {apiGetAllRestaurants, apiGetMyRestaurants} from "./Actions";
import {RestaurantList} from "../../components/Restaurant/RestaurantList";
import {useHistory} from "react-router-dom";

const Restaurants = () =>{

    let [data,setData] = useState(null);
    let [loading, setLoading] = useState(false);
    const email = localStorage.getItem("email");

    const history = useHistory();

    useEffect(()=>{
        init();
    },[])

    const init = () =>{
        const role = localStorage.getItem("role");
        const path = window.location.pathname;
        if(path==="/restaurant/my" && role==="Admin"){
            getMyRestaurants();
        }else if(path==="restaurant/my"){
            history.push("/");
        }else{
            getAllRestaurants();
        }
    }

    const getMyRestaurants = ()=>{
        setLoading(true);
        apiGetMyRestaurants(email,(data)=>{
            setData(data);
            setLoading(false);
        },(error)=>{
            setData(null);
            setLoading(false);
        })
    }

    const getAllRestaurants = () =>{
        setLoading(true);
        apiGetAllRestaurants((data)=>{
            setData(data);
            setLoading(false);
        },(error)=>{
            setData(null);
            setLoading(false);
        })
    }

    if(loading) return <>Načítám restaurace</>
    if(!data) return <>Žádné restaurace nenalezeny</>
    if(data) return <RestaurantList restaurants={data}/>
}

export default Restaurants;