import React from "react";

import {axios} from "../../axiosConfig";

const url = "/api/v1/photo"

export const apiGetPhotoById = (id, callback, error) => {
    axios.get(url + "/" + id,{
        responseType: "blob"
    })
        .then(response =>{
            const url = URL.createObjectURL(new Blob([response.data]));
            callback(url);
        })
        .catch(err => error(err));
}