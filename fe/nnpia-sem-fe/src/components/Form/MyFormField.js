import React from "react";
import {Field} from "react-final-form";

const MyFormField = ({
    name,
    type,
    inputClassName,
    labelClassName,
    label
                     })=>{

    return(
        <Field name={name}>
            {({input, meta}) => (
                <>
                    <div className={labelClassName}>{label}</div>
                    <div className={inputClassName}>
                        <input {...input} type={type} className={"form-control"}/>
                        {meta.error && meta.touched &&
                            <div className="text-warning">{meta.error}</div>}
                    </div>

                </>
            )}
        </Field>
    )
}

export default MyFormField;