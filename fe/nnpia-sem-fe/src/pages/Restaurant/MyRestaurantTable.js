import React from "react";
import MyTable from "../../components/Table/MyTable";
import {AiFillDelete, AiFillEdit, AiFillStar, AiOutlineStar} from "react-icons/ai";
import {Link, useHistory} from "react-router-dom";
import {apiDeleteRestaurant} from "./Actions";
import cogoToast from "cogo-toast";

const MyRestaurantTable = ({data}) => {

    const history = useHistory();

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

    const deleteRestaurant = (id) =>{
        apiDeleteRestaurant(id,(data)=>{
            cogoToast.success("Restaurace byla odstraněna");
            history.push("/");
        },(error)=>{
            cogoToast.error("Nepodařilo se odstranit restauraci")
        })
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
        },
        {
            Header: "Odstranit",
            accessor: d => (
                    <button className={'rounded-circle bg-info text-white'} onClick={()=>deleteRestaurant(d.id)}><AiFillDelete size={15}/></button>
            )
        }
    ], [])
    const rows = React.useMemo(() => {
        return data;
    }, [])

    return <MyTable columns={columns} data={rows}/>
}
export default MyRestaurantTable;