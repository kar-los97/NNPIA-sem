import React from "react";
import {usePagination, useTable,useSortBy} from "react-table";
import {FiChevronDown, FiChevronsLeft, FiChevronsRight, FiChevronUp} from "react-icons/fi";

const MyTable = ({columns,data}) =>{
    const {
        getTableProps,
        getTableBodyProps,
        headerGroups,
        prepareRow,
        page,
        canPreviousPage,
        canNextPage,
        pageOptions,
        pageCount,
        gotoPage,
        nextPage,
        previousPage,
        setPageSize,
        selectedFlatRows,
        state: {pageIndex, pageSize, selectedRowIds},
    } = useTable({
        columns,
        data,
        initialState: {pageIndex: 0, hideOnSinglePage: true},
    }, useSortBy,usePagination);

    return (
        <>
            <div className={"relative overflow-x-auto"}>
                <table className={"table striped"} {...getTableProps()}>
                    <thead className={"table-header-group"}>
                    {headerGroups.map((headerGroup,index) => (
                        <tr {...headerGroup.getHeaderGroupProps()} key={index.toString()}>
                            {headerGroup.headers.map((column,index) => (
                                <th {...column.getHeaderProps(column.getSortByToggleProps())} key={index.toString()}>
                                    {column.render('Header')}
                                    {column.isSorted?column.isSortedDesc?<FiChevronDown/>:<FiChevronUp/>:<></>}
                                </th>
                            ))}
                        </tr>
                    ))}
                    </thead>
                    <tbody {...getTableBodyProps()} className={""}>
                    {data.length===0?<tr><td colSpan={columns.length}>Žádná data k zobrazení</td></tr>:
                        page.map((row, i) => {
                            prepareRow(row)
                            return (
                                <tr {...row.getRowProps()} className={"table-row"} key={i.toString()}>
                                    {row.cells.map((cell,i) => {
                                        return <td {...cell.getCellProps()}
                                                   className={"table-cell "} key={i.toString()}>{cell.render('Cell')}</td>
                                    })}
                                </tr>
                            )
                        })}
                    </tbody>
                </table>
            </div>
            {10 <= data.length ?
                <div className="d-flex flex-row justify-content-center">
                    <button className={'rounded-circle bg-info text-white m-2'}
                            onClick={() => previousPage()} disabled={!canPreviousPage}>
                        <FiChevronsLeft/>
                    </button>
                    {' '}
                    <span className={"form-label m-2"}>
                    Stránka{' '}
                        <strong>{pageIndex + 1} z {pageOptions.length}</strong>{' '}
                </span>
                    <button className={'rounded-circle bg-info text-white m-2'}
                            onClick={() => nextPage()} disabled={!canNextPage}>
                        <FiChevronsRight/>
                    </button>
                    {' '}
                    <span className={"form-label"}>
          | Přejít na stránku:{' '}
                        <input
                            className={"m-2"}
                            type="number"
                            defaultValue={pageIndex + 1}
                            onChange={e => {
                                const page = e.target.value ? Number(e.target.value) - 1 : 0
                                gotoPage(page)
                            }}
                            style={{width: '70px'}}
                        />
        </span>{' '}
                    <select
                        className={"m-2"}
                        value={pageSize}
                        onChange={e => {
                            setPageSize(Number(e.target.value))
                        }}
                    >
                        {[10, 20, 30, 40, 50].map(pageSize => (
                            <option key={pageSize} value={pageSize}>
                                {pageSize} záznamů
                            </option>
                        ))}
                    </select>
                </div> : <></>}
        </>
    )

}
export default MyTable;