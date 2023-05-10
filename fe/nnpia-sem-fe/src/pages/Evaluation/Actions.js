import React from "react";

import {axios} from "../../axiosConfig";

const url = "/api/v1/evaluation"

export const apiPostCreateEvaluation = (data, callback, error) => {
    axios.post(url, data)
        .then(response => callback(response.data))
        .catch(err => error(err));
}

export const apiGetAllMyEvaluation = (email, callback, error) => {
    axios.get(url + "/user/" + email)
        .then(response => callback(response.data))
        .catch(err => error(err));
}

export const apiDeleteEvaluation = (id,callback,error)=>{
    axios.delete(url + "/" + id)
        .then(response => callback(response.data))
        .catch(err => error(err));
}