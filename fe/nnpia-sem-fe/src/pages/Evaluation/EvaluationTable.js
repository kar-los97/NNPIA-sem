import React from "react";
import {AiFillDelete, AiFillStar, AiOutlineStar} from "react-icons/ai";
import MyTable from "../../components/Table/MyTable";
import {Link, useHistory} from "react-router-dom";
import {apiDeleteRestaurant} from "../Restaurant/Actions";
import cogoToast from "cogo-toast";
import {apiDeleteEvaluation} from "./Actions";

export const EvaluationTable = ({data}) =>{
    const history = useHistory();

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

    const deleteEvaluation = (id)=>{
        apiDeleteEvaluation(id,(data)=>{
            cogoToast.success("Hodnocení bylo odstraněno byla odstraněna");
            history.push("/");
        },(error)=>{
            cogoToast.error("Nepodařilo se odstranit hodnocení");
        })
    }

    const columns = React.useMemo(() => [
        {
            Header: 'Datum',
            accessor: d=> (new Date(d.createdAt).toLocaleString('cs')),
        },
        {
            Header: 'Komentář',
            accessor: "comment"
        },
        {
            Header: "Hodnocení",
            accessor: d => getStarsIcons(d.stars)
        },
        {
            Header: "Restaurace",
            accessor: d=><Link to={"/restaurant/evaluation/"+d.restaurantId}>{d.restaurantId}</Link>
        },
        {
            Header: "Odstranit",
            accessor: d => (
                <button className={'rounded-circle bg-info text-white'} onClick={()=>deleteEvaluation(d.id)}><AiFillDelete size={15}/></button>
            )
        }
    ], [])

    const rows = React.useMemo(() => {
        return data;
    }, [])

    return <MyTable columns={columns} data={rows}/>
}