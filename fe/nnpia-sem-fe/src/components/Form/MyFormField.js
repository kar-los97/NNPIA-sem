import React from "react";
import {Field} from "react-final-form";

const MyFormField = ({
    name,
    type,
    inputClassName,
    labelClassName,
    label,
    onChange = null,
    placeHolder
                     })=>{

    if(type==="file"){
        return(
            <Field name={name}>
                {({input, meta}) => (
                    <>
                        <div className={labelClassName}>{label}</div>
                        <div className={inputClassName}>
                            <input {...input} type={"file"} maxLength={10} accept={"image/png, image/jpeg"} className={"form-control"} onChange={onChange} placeholder={placeHolder}/>
                            {meta.error && meta.touched &&
                                <div className="text-warning">{meta.error}</div>}
                        </div>

                    </>
                )}
            </Field>
        )
    }else{
        return(
            <Field name={name}>
                {({input, meta}) => (
                    <>
                        <div className={labelClassName}>{label}</div>
                        <div className={inputClassName}>
                            {onChange?<input {...input} type={type} className={"form-control"} onChange={onChange} placeholder={placeHolder}/>:
                                <input {...input} type={type} className={"form-control"} placeholder={placeHolder}/>}
                            {meta.error && meta.touched &&
                                <div className="text-warning">{meta.error}</div>}
                        </div>

                    </>
                )}
            </Field>
        )
    }


}

export default MyFormField;