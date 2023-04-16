import React, {useEffect, useState} from "react";
import MyForm from "../../components/Form/MyForm";
import MyFormField from "../../components/Form/MyFormField";
import {apiLoginUser} from "./Actions";
import {useHistory} from "react-router-dom";
import {axios} from "../../axiosConfig";

const LoginForm = ({setLoggedInUser}) => {
    let [saving, setSaving] = useState(false);
    let history = useHistory();

    useEffect(() => {

    }, [])

    const submit = (values) => {
        setSaving(true);
        alert(JSON.stringify(values));
        apiLoginUser(values,(result)=>{
            alert(JSON.stringify(result));
            localStorage.setItem('role',result.role);
            localStorage.setItem('email',result.email);
            axios.defaults.headers.common['Authorization'] = "Bearer "+result.token;
            setLoggedInUser(result);
            history.push("/");
            setSaving(false);
        },(error)=>{
            setSaving(false);
        })
    }


    return (
        <MyForm
            onSubmit={submit}
            validate={values => {
                let errors = {};
                if (!values.password) {
                    errors.password = "Zadejte heslo"
                }
                if (values.email && !values.email.match("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$")) {
                    errors.email = "Zadejte validní email"
                }
                if (!values.email) {
                    errors.email = "Zadejte email"
                }

                return errors;
            }}
            render={(handleSubmit) => (
                <div className={"container-fluid p-3"}>
                    <div className={"row mb-3"}>
                        <MyFormField type={"email"} name={"email"} inputClassName={"col-sm-10"} label={"Email:"}
                                     labelClassName={"col-sm-2 col-form-label"}/>
                    </div>
                    <div className={"row mb-3"}>
                        <MyFormField type={"password"} name={"password"} inputClassName={"col-sm-10"} label={"Heslo:"}
                                     labelClassName={"col-sm-2 col-form-label"}/>
                    </div>
                    <button disabled={saving} onClick={handleSubmit} className={"btn bg-info text-white"}>Přihlásit se
                    </button>
                </div>
            )
            }
        />
    );
}

export default LoginForm;