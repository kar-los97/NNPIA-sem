import React, {useEffect, useState} from "react";
import {Link, useHistory, useParams} from "react-router-dom";
import {apiCreateRestaurant, apiGetRestaurantById} from "./Actions";
import MyForm from "../../components/Form/MyForm";
import MyFormField from "../../components/Form/MyFormField";
import cogoToast from "cogo-toast";

const RestaurantForm = () => {
    let [loading, setLoading] = useState(false);
    let [saving, setSaving] = useState(false);
    let [data, setData] = useState(null);
    let [selectedFile, setSelectedFile] = useState(null);
    const {id} = useParams();
    let history = useHistory();

    useEffect(() => {
        if (id) {
            setLoading(true);
            apiGetRestaurantById(id, (data) => {
                setData(data);
                setLoading(false);
            }, (err) => {
                setLoading(false);
                setData(null);
                cogoToast.error("Nepodařilo se načíst detail restaurace");
                history.push("/restaurant/my")
            })
        }
    }, [])
    const submit = (values) => {
        alert(JSON.stringify(values));
        let data = new FormData();
        data.append('restaurant', new Blob([JSON.stringify(values)], {
            type: "application/json"
        }));
        data.append('photoTitle', selectedFile);
        setSaving(true);
        apiCreateRestaurant(data, (data) => {
            cogoToast.success("Úspěšně uloženo.");
            history.push("/restaurant/my/detail/"+data.id);
            setSaving(false);
        }, (error) => {
            cogoToast.error("Nastala chyba při ukládání");
            setSaving(false);
        })
    }

    if (loading) return <>Načítám data pro restauraci</>
    else {
        return (
            <div className={"d-flex flex-column min-vh-100 justify-content-center align-items-center"}
                 style={{height: "80vh"}}>
                <div className={"card w-100 text-center align-content-center shadow-lg"}>
                    <div className={"card-header bg-info justify-content-center text-center"}>
                        <div className={"text-xl text-capitalize"}><h2 className={"display-4"}>Restaurace</h2></div>
                        <div className={"card-subtitle"}><h3 className={"display-6"}>{id ? "Editace" : "Přidání"}</h3>
                        </div>

                    </div>
                    <div className={"card-body bg-light"}>
                        <MyForm initialValues={data} onSubmit={submit}
                                validate={(values) => {
                                    let error = {};
                                    if(!values.name || values.name===""){
                                        error.name="Zadejte název";
                                    }if(!selectedFile && !id){
                                        error.file = "Vyberte prosím titulní obrázek"
                                    }if(!values.address || values.address===""){
                                        error.address="Zadejte adresu";
                                    }if(!values.note || values.note===""){
                                        error.note="Zadejte poznámku";
                                    }


                                    return error;
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
                                            <MyFormField onChange={(e) => {
                                                e.preventDefault();
                                                setSelectedFile(e.target.files[0]);
                                            }} type={"file"} name={"file"} inputClassName={"col-sm-10 mt-3"}
                                                         label={"Titulní fotografie:"}
                                                         labelClassName={"col-sm-2 col-form-label mt-3"}/>
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