import React, {useEffect, useState} from "react";
import {useParams} from "react-router-dom";
import {apiCreateRestaurant, apiGetRestaurantById} from "./Actions";
import MyForm from "../../components/Form/MyForm";
import MyFormField from "../../components/Form/MyFormField";

const RestaurantForm = () => {
    let [loading, setLoading] = useState(false);
    let [saving, setSaving] = useState(false);
    let [data, setData] = useState(null);
    let [selectedFile, setSelectedFile] = useState(null);
    const {id} = useParams();

    useEffect(() => {
        if (id) {
            setLoading(true);
            apiGetRestaurantById(id, (data) => {
                setData(data);
                setLoading(false);
            }, (err) => {
                setLoading(false);
                setData(null);
            })
        }
    }, [])
    const submit = (values) => {
        alert(JSON.stringify(values));
        values.photoTitle = selectedFile;
        setSaving(true);
        apiCreateRestaurant(values,(data)=>{
            alert("SUCCES");
            setSaving(false);
        },(error)=>{
            alert("ERROR");
            setSaving(false);
        })
    }

    if (loading) return <>Načítám data pro restauraci</>
    else {
        return (
            <div className={"d-flex align-items-center justify-content-center"}>
                <div className={"card w-100 text-center align-content-center"}>
                    <div className={"card-header bg-info justify-content-center text-center"}>
                        <div className={"text-xl text-capitalize"}>Restaurace</div>
                        <div className={"card-subtitle"}>{id ? "Editace" : "Přidání"}</div>

                    </div>
                    <div className={"card-body bg-light"}>
                        <MyForm initialValues={data} onSubmit={submit}
                                validate={(values) => {

                                }}
                                render={(handleSubmit) => (
                                    <div className={"container-fluid p-3"}>
                                        <div className={"row mb-3"}>
                                            <MyFormField type={"text"} name={"name"} inputClassName={"col-sm-10"}
                                                         label={"Název:"}
                                                         labelClassName={"col-sm-2 col-form-label"}/>

                                        </div>
                                        <div className={"row mb-3"}>
                                            <MyFormField type={"text"} name={"address"} inputClassName={"col-sm-10"}
                                                         label={"Adresa:"}
                                                         labelClassName={"col-sm-2 col-form-label"}/>
                                        </div>
                                        <div className={"row mb-3"}>
                                            <MyFormField type={"text"} name={"note"} inputClassName={"col-sm-10"}
                                                         label={"Poznámka:"}
                                                         labelClassName={"col-sm-2 col-form-label"}/>
                                            <MyFormField onChange={(e)=>{
                                                e.preventDefault();
                                                setSelectedFile(e.target.files[0]);}} type={"file"} name={"file"} inputClassName={"col-sm-10"}
                                                         label={"Titulní fotografie:"}
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
}

export default RestaurantForm;