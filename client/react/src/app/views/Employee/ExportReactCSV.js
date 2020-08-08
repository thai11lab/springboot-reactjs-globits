import React from "react";
import {
    IconButton,
    Table,
    TableHead,
    TableBody,
    TableRow,
    TableCell,
    Icon,
    TablePagination,
    Button,
    Card,
    TextField,
    InputAdornment,
    Grid,
  } from "@material-ui/core";
import * as FileSaver from 'file-saver';
import * as XLSX from 'xlsx';

export const ExportReactCSV  =({csvData,fileName})=>{
    const styleButton={
        height:"40px", 
        position:"relative",
        left:"500px"
      };
    const fileType = 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8';
    const fileExtension = '.xlsx';

    const exportToCSV = (csvData, fileName) => {
        const ws = XLSX.utils.json_to_sheet(csvData);
        const wb = { Sheets: { 'data': ws }, SheetNames: ['data'] };
        const excelBuffer = XLSX.write(wb, { bookType: 'xlsx', type: 'array' });
        const data = new Blob([excelBuffer], {type: fileType});
        FileSaver.saveAs(data, fileName + fileExtension);
    }

    return(
        <Button onClick={(e)=>exportToCSV(csvData,fileName)} 
                style={styleButton}
                className="mb-16"
                variant="contained"
                color="primary"
        >Tải xuống Excell</Button>
    )
}