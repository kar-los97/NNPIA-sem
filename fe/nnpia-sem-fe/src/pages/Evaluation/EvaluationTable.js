import React from "react";
import {Link} from "react-router-dom";
import {AiFillStar, AiOutlineStar} from "react-icons/ai";
import MyTable from "../../components/Table/MyTable";

export const EvaluationTable = ({data}) =>{

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
        }
    ], [])

    const rows = React.useMemo(() => {
        return data;
    }, [])

    return <MyTable columns={columns} data={rows}/>
}