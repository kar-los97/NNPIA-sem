import React, {useEffect, useRef, useState} from "react";
import {Link} from "react-router-dom";
import MyRestaurantTable from "../../pages/Restaurant/MyRestaurantTable";
import RestaurantTable from "../../pages/Restaurant/RestaurantTable";
import {apiGetFilterRestaurants} from "../../pages/Restaurant/Actions";
import cogoToast from "cogo-toast";

export const RestaurantList = ({restaurants}) => {
    let role = localStorage.getItem("role");
    const path = window.location.pathname;
    const [filter, setFilter] = useState(null);

    const timer = useRef(null);



    const filterData = () => {
        if(filter==="" || filter === null) return restaurants;
        apiGetFilterRestaurants(filter, (data) => {
            return data;
        }, (error) => {
            cogoToast.error("Nepodařilo se nalézt požadované záznamy.")
            return null;

        })
    }
    const data = React.useMemo(() => {
        return filterData();
    }, [filter])

    const onChange = (event) => {
        clearTimeout(timer.current);
        timer.current = setTimeout(() => {
            setFilter(event.target.value)
        }, 1000)
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
        <div className={"card-subtitle align-items-center"}>
            <input className={"w-100"} placeholder={"Zadejte hledaný výraz"} type={"text"} name={"filter"}
                   onChange={onChange}/>
        </div>
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