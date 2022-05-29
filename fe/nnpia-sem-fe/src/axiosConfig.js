import axiosLib from "axios";

export const axios = axiosLib.create({

    baseURL: " http://localhost:8080/",
    headers: {
        'Content-Type': 'application/json',
    }
});