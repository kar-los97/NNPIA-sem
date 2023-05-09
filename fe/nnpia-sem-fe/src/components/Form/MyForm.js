import React from "react";
import {Form} from "react-final-form";

const MyForm = ({
                    validate,
                    onSubmit,
                    render,
                    initialValues = null
                }) => {

    return (
        <Form  initialValues={initialValues} onSubmit={onSubmit}
              validate={values => validate(values)}
              render={({handleSubmit}) => render(handleSubmit)}
        />
    );

}

export default MyForm;