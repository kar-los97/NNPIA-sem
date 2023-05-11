import React, {useEffect, useRef, useState} from "react";
import {Link} from "react-router-dom";
import MyRestaurantTable from "../../pages/Restaurant/MyRestaurantTable";
import RestaurantTable from "../../pages/Restaurant/RestaurantTable";
import {apiGetFilterRestaurants} from "../../pages/Restaurant/Actions";
import cogoToast from "cogo-toast";
import MyForm from "../Form/MyForm";
import MyFormField from "../Form/MyFormField";

export const RestaurantList = ({restaurants}) => {
    let role = localStorage.getItem("role");
    const path = window.location.pathname;
    const [data,setData] = useState(null);



    const filterData = (data) => {
        if(!data || data.filter==="" || data.filter === null ||data.filter === undefined) setData(restaurants);
        else{
            apiGetFilterRestaurants(data.filter, (data) => {
                setData(data);
            }, (error) => {
                cogoToast.error("Nepodařilo se nalézt požadované záznamy.")
                setData(restaurants);
            })
        }

    }


    const AddButton = () => (
        <div className={"ml-auto"}>
            <Link to={"/restaurant/my/add"}>
                <button
                    className={"btn bg-dark text-white"}>
                    Přidat
                </button>
            </Link>
        </div>
    )


    const Title = () => (
        <div className={"card-title align-items-center"}>{role === "Admin" && path === "/restaurant/my" ?
            <div className={"d-flex flex-row w-100"}><h2 className={"display-6"}>Moje
                restaurace</h2> <AddButton/></div> : <h2 className={"display-6"}>Všechny
                restaurace</h2>}</div>
    )
    const FilterForm = () => (

            <MyForm validate={(values)=>{

            }} render={(handleSubmit) => (
                <div className={"card-subtitle align-items-center d-flex flex-row"}>
                    <MyFormField type={"text"} name={"filter"} placeHolder={"Zadejte hledaný výraz"} inputClassName={"w-100"} label={""}/>
                    <button  onClick={handleSubmit}
                            className={"btn bg-info text-white m-2"}>Filtrovat
                    </button>
                </div>
            )
            } onSubmit={filterData}/>
    )

    return (
        <div className={"card w-100 shadow-lg mt-3"}>
            <div className={"card-header bg-info pt-lg-4"}>
                <Title/>

            </div>
            <div className={"card-body"}>
                {path !== "/restaurant/my" && <FilterForm/>}
            </div>
            {role === "Admin" && path === "/restaurant/my" ?
                <MyRestaurantTable data={data ? data : restaurants}/> :
                <RestaurantTable data={data ? data : restaurants}/>
            }

            {/*<div className={"card-body row"}>
                {restaurants.map((item, index) => (
                    <RestaurantCard restaurant={item}/>
                ))}
            </div>*/}
        </div>
    );


}