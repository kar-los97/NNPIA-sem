import React, {useEffect, useRef, useState} from "react";
import {useHistory, useParams} from "react-router-dom";
import MyForm from "../../components/Form/MyForm";
import MyFormField from "../../components/Form/MyFormField";
import {apiGetRestaurantById} from "../Restaurant/Actions";
import {apiPostCreateEvaluation} from "./Actions";
import cogoToast from "cogo-toast";

export const EvaluationForm = () => {
    const {id} = useParams();
    let[loading,setLoading] = useState(false);
    let[saving,setSaving] = useState(false);
    const history = useHistory();
    let [data,setData] = useState(null);

    useEffect(()=>{
        if(id){
            setLoading(true);
            apiGetRestaurantById(id,(data)=>{
                setData(data);
                setLoading(false);
            },(error)=>{
                setData(null);
                setLoading(false);
            })
        }else{
            history.push("/restaurant")
        }
    },[])

    const onSubmit = (values) => {
        setSaving(true);
        values.restaurantId = id;
        setSaving(true);
        apiPostCreateEvaluation(values,(data)=>{
            cogoToast.success("Hodnocení bylo vytvořeno");
            history.push("/restaurant")
            setSaving(false);
        },(error)=>{
            cogoToast.error("Nepodařilo se vytvořit hodnocení");
            setSaving(false);
        })

    }
    if(loading) return <>Načítám data pro hodnocení...</>
    if(!data) return <>Žádná data k zobrazení</>
    return (
        <div className={"d-flex flex-column min-vh-100 justify-content-center align-items-center"} style={{height:"80vh"}}>
            <div className={"card w-100 text-center align-content-center shadow-lg"}>
                <div className={"card-header bg-info justify-content-center text-center"}>
                    <div className={"text-xl text-capitalize"}><h2 className={"display-4"}>Restaurace: {data.name}</h2></div>
                    <div className={"card-subtitle"}><h3 className={"display-6"}>Udělení hodnocení</h3></div>

                </div>
                <div className={"card-body bg-light"}>
                    <MyForm onSubmit={onSubmit}
                            validate={(values) => {
                                let error = {};
                                if(values.stars && (values.stars<0 || values.stars>5)){
                                    error.stars = "Zadejte validní počet hvězdiček hodnocení";
                                }
                                return error;
                            }}
                            render={(handleSubmit) => (
                                <div className={"container-fluid p-3"}>
                                    <div className={"row mb-3"}>
                                        <MyFormField type={"number"} name={"stars"} inputClassName={"col-sm-10"}
                                                     label={"Hodnocení:"}
                                                     labelClassName={"col-sm-2 col-form-label"}/>

                                    </div>
                                    <div className={"row mb-3"}>
                                        <MyFormField type={"text"} name={"comment"} inputClassName={"col-sm-10"}
                                                     label={"Komentář:"}
                                                     labelClassName={"col-sm-2 col-form-label"}/>

                                    </div>
                                    <button disabled={saving} onClick={handleSubmit}
                                            className={"btn bg-info text-white"}>Odeslat
                                    </button>
                                </div>
                            )
                            }/>
                </div>

            </div>
        </div>

    )
}