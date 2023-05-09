import React, {useEffect, useState} from "react";
import {Link} from "react-router-dom";
import {apiGetPhotoById} from "../../pages/Photo/Actions";
import {AiFillStar, AiOutlineStar} from "react-icons/ai";

export const RestaurantCard = ({restaurant}) => {
    let [photo, setPhoto] = useState();
    let [loading, setLoading] = useState(false);
    useEffect(() => {
        if (restaurant.photoId) {
            getPhoto(restaurant.photoId);
        }

    }, [])

    const getStarsIcons = (stars) => {
        let starCount = [];
        for (let i = 0; i < stars; i++) {
            starCount.push(1);
        }
        for (let i = 5; i > stars; i--) {
            starCount.push(0);
        }
        let iconsD = starCount.map(s => {
            if (s === 1) return (<AiFillStar size={30}/>)
            else return (<AiOutlineStar size={30}/>)
        })
        return (<div className={"d-flex flex-row"}>{iconsD}</div>);
    }

    const getPhoto = (id) => {
        setLoading(true);
        apiGetPhotoById(id, (photo) => {
            setLoading(false);
            setPhoto(photo);
        }, (error) => {
            setLoading(false);
            setPhoto(null);
        })
    }

    let role = localStorage.getItem("role");
    const path = window.location.pathname;
    const Name = () => {
        if (role === "Admin" && path === "/restaurant/my") {
            return (
                <h5 className={"text-white display-6"}><Link
                    className={"text-decoration-none text-white text-uppercase"}
                    to={"/restaurant/detail/" + restaurant.id}>{restaurant.name}</Link>
                </h5>
            )
        } else {
            return <h5 className={"text-white display-6 text-uppercase"}>Restaurace: {restaurant.name}</h5>
        }
    }

    return (
        <div className={"d-flex flex-column min-vh-100 justify-content-center align-items-center m-2"}>
            <div className={"w-100 text-center align-content-center shadow-lg"}>
                <div className={"card-header bg-dark justify-content-center text-center"}>
                    <div className={"card-title"}><Name/></div>
                </div>
                <div className={"card-body bg-light"}>
                    <div className={"card-text w-100 justify-content-center"}>
                        {!loading && photo &&
                            <div style={{
                                backgroundImage: `url(${photo})`,
                                backgroundRepeat: "no-repeat",
                                backgroundSize: "contain",
                                backgroundPosition: "center",
                                height: "30vh"
                            }}></div>}

                        <div className={"row justify-content-center w-100 mt-2"}>
                            <span>{restaurant.address}</span>
                        </div>
                        <div className={"row row-cols-5 justify-content-center w-100 mt-3"}>
                            {getStarsIcons(restaurant.stars)}
                        </div>

                    </div>
                </div>
            </div>
        </div>

    )

}