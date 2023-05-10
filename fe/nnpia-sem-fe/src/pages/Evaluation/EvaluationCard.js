import React, {useState} from "react";
import {RestaurantEvaluationTable} from "./RestaurantEvaluationTable";
import {EvaluationForm} from "./EvaluationForm";
import {Link, useParams} from "react-router-dom";

export const EvaluationCard = ({evaluation}) => {

    let [openModal, setOpenModal] = useState(false);
    const {id} = useParams();
    const Name = () => {

        return <h5 className={"text-white display-6 text-uppercase"}>Hodnocení</h5>

    }

    const EvaluateButton = () => (
        <div className={"ml-auto"}>
            <Link to={"/restaurant/evaluation/add/"+id}>
                <button onClick={() => setOpenModal(true)}
                        className={"btn bg-info text-white"}>
                    Ohodnotit
                </button>
            </Link>
        </div>
    )

    return (
        <div className={"d-flex flex-column min-vh-100 justify-content-center align-items-center m-2"}>
            <div className={"w-100 text-center align-content-center shadow-lg"}>
                <div className={"card-header bg-dark justify-content-center text-center"}>
                    <div className={"card-title m-2"}>
                        <div className={"d-flex flex-row w-100"}><Name/><EvaluateButton/></div>
                    </div>
                </div>
                <div className={"card-body bg-light"}>
                    <RestaurantEvaluationTable data={evaluation}/>
                </div>
            </div>
        </div>

    )

}