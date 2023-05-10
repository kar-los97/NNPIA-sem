import React, {useEffect, useState} from "react";
import {apiGetAllMyEvaluation, apiGetAllRestaurants, apiGetMyRestaurants} from "./Actions";
import {RestaurantList} from "../../components/Restaurant/RestaurantList";
import {useHistory} from "react-router-dom";
import {EvaluationTable} from "./EvaluationTable";
import MyRestaurantTable from "../Restaurant/MyRestaurantTable";
import RestaurantTable from "../Restaurant/RestaurantTable";

const EvaluationList = () => {

    let [data, setData] = useState(null);
    let [loading, setLoading] = useState(false);


    useEffect(() => {
        init();
    }, [])

    const init = () => {
        const email = localStorage.getItem("email");
        getAllMyEvaluations(email);
    }


    const getAllMyEvaluations = (email) => {
        setLoading(true);
        apiGetAllMyEvaluation(email, (data) => {
            setData(data);
            setLoading(false);
        }, (error) => {
            setData(null);
            setLoading(false);
        })
    }
    const Title = () => (
        <div className={"card-title align-items-center"}>
            <div className={"d-flex flex-row w-100"}><h2 className={"display-6"}>Všechny má hodnocení</h2></div>
        </div>
    )

    if (loading) return <>Načítám tvá hodnocení</>
    if (!data) return <>Žádné hodnocení nenalezeno</>
    if (data)
        return (
            <div className={"card w-100 shadow-lg mt-3"}>
                <div className={"card-header bg-info pt-lg-4"}>
                    <Title/>

                </div>
                <EvaluationTable data={data}/>
                {/*<div className={"card-body row"}>
                {restaurants.map((item, index) => (
                    <RestaurantCard restaurant={item}/>
                ))}
            </div>*/}
            </div>
        );

}

export default EvaluationList;