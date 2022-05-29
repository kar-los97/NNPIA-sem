import React from "react";
import {RestaurantCard} from "./RestaurantCard";
import {Link} from "react-router-dom";

export const RestaurantList = ({restaurants}) => {
    let role = localStorage.getItem("role");
    const path = window.location.pathname;
    const AddButton = () => (
        <Link to={"/restaurant/my/add"}>
            <button
                className={"btn bg-dark text-white"}>
                Přidat
            </button>
        </Link>
    )

    const Title = () => (
        <div className={"card-title"}>{role === "Admin" && path==="/restaurant/my" ? <>Moje restaurace <AddButton/></> : <>Všechny
            restaurace</>}</div>
    )


    return (
        <>
            <div className={"card w-100"}>
                <div className={"card-header bg-info"}>
                    <Title/>

                </div>
                <div className={"card-body bg-light"}>
                    <div className={"d-flex flex-row"}>
                        {restaurants.map((item, index) => (
                            <RestaurantCard restaurant={item}/>
                        ))}
                    </div>
                </div>
            </div>


        </>
    )


}