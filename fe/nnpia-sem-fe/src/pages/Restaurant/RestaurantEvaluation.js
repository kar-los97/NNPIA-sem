import React, {useEffect} from "react";
import {useHistory, useParams} from "react-router-dom";
import {useState} from "react";
import MyForm from "../../components/Form/MyForm";
import MyFormField from "../../components/Form/MyFormField";
import {apiGetRestaurantById} from "./Actions";
import cogoToast from "cogo-toast";
import {RestaurantCard} from "../../components/Restaurant/RestaurantCard";
import {EvaluationCard} from "../Evaluation/EvaluationCard";

const RestaurantEvaluation = () => {
    let [loading, setLoading] = useState(false);
    let [saving, setSaving] = useState(false);
    let [data, setData] = useState(null);
    const {id} = useParams();
    const history = useHistory();

    useEffect(() => {
        if (id) {
            setLoading(true);
            apiGetRestaurantById(id, (data) => {
                setData(data);
                setLoading(false);
            }, (err) => {
                setData(null);
                cogoToast.error("Nepodařilo se načíst data pro hodnocení restaurace");
                history.push("/restaurant");
                setLoading(false);
            })
        } else {
            history.push("/restaurant");
        }
    }, [])

    const submit = () => {

    }

    if (loading) return <>Načítám data pro hodnocení restaurace</>
    if (!data) return <>Žádná data k zobrazení</>
    else {
        return (
            <>
                <RestaurantCard restaurant={data}/>
                <EvaluationCard evaluation={data.evaluation}/>
            </>

        )
    }

}
export default RestaurantEvaluation;