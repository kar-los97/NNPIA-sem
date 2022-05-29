import React from "react";
import {Link} from "react-router-dom";

export const RestaurantCard = ({restaurant}) => {
    let role = localStorage.getItem("role");
    const path = window.location.pathname;
    const Name = () => {
        if (role === "Admin" && path==="/restaurant/my") {
            return (
                <Link className={"text-decoration-none text-white"} to={"/restaurant/detail/" + restaurant.id}>{restaurant.name}</Link>
            )
        } else {
            return <>{restaurant.name}</>
        }
    }
    return (
        <div className={"card w-25 m-2"}>
            <div className={"card-header bg-dark justify-content-center text-center"}>
                <div className={"card-title"}><Name/></div>
            </div>
            <div className={"card-body bg-light"}>
                <div className={"card-text"}>
                    {restaurant.address}
                </div>
            </div>
        </div>

    )
}