import React from "react";
import MyTable from "../../components/Table/MyTable";
import {AiFillEdit, AiFillStar, AiOutlineStar} from "react-icons/ai";
import {Link} from "react-router-dom";
import {FiEdit} from "react-icons/fi";

const MyRestaurantTable = ({data}) => {


    const getStarsIcons=(stars)=>{
        let starCount = [];
        for(let i = 0; i<stars;i++){
            starCount.push(1);
        }
        for(let i = 5; i>stars;i--){
            starCount.push(0);
        }
        let iconsD = starCount.map(s=>{
            if(s===1) return (<AiFillStar size={20}/> )
            else return (<AiOutlineStar size={20}/> )
        })
        return(<div className={"d-flex flex-row"}>{iconsD}</div>);
    }
    const columns = React.useMemo(() => [
        {
            Header: 'Adresa',
            accessor: 'address',
        },
        {
            Header: 'Název',
            accessor: "name"
        },
        {
            Header: "Průměrné hodnocení",
            accessor: d=> getStarsIcons(d.stars)
        },
        {
            Header: "Upravit",
            accessor: d => (
                <Link to={"/restaurant/my/detail/"+d.id}>
                    <button className={'rounded-circle bg-info text-white'}><AiFillEdit size={15}/></button>
                </Link>
            )
        }
    ], [])
    const rows = React.useMemo(() => {
        return data;
    }, [])

    return <MyTable columns={columns} data={rows}/>
}
export default MyRestaurantTable;