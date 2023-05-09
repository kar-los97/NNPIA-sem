import React from "react";
import {Field} from "react-final-form";

const MyFileField = ({
                         name,
                         type,
                         inputClassName,
                         labelClassName,
                         label,
                         onChange = null
                     })=>{

        return(
            <Field name={name}>
                {({input, meta}) => (
                    <>
                        <div className={labelClassName}>{label}</div>
                        <div className={inputClassName}>
                            {onChange?<input {...input} type={type} className={"form-control"} onChange={onChange}/>:
                                <input {...input} type={type} className={"form-control"}/>}
                            {meta.error && meta.touched &&
                                <div className="text-warning">{meta.error}</div>}
                        </div>

                    </>
                )}
            </Field>
        )



}

export default MyFileField;