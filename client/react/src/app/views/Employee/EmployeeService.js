import axios from "axios";

export const getAllEmployees = () => {
  //return axios.get("/api/user/all");
  //alert( axios.defaults.headers.common["Authorization"]);
  return axios.get("http://localhost:8092/sample/api/employee");  
};
//http://localhost:8081/shop/api/employees
export const getUserById = id => {
  return axios.get("http://localhost:8092/sample/api/employee/"+id);
};
export const deleteUser = User => {
  return axios.delete("http://localhost:8092/sample/api/employee/delete/"+User.id, User);
};  
export const addNewUser = User => {
  //"/api/user/add"
  return axios.post("http://localhost:8092/sample/api/employee/add", User);
};

export const updateUser = User =>{
  return axios.put("http://localhost:8092/sample/api/employee/update/"+User.id,User);
};

export const getAllEmployeesBySearch = (query,page,rowsPerPage) =>{
    var pageIndex = page+1;
    var params= pageIndex+"/"+rowsPerPage;
    return axios.get("http://localhost:8092/sample/api/employee/search/"+query+"/"+params);
}

export const exportFileExcell1 = query =>{
  if(query !="" && query !=null){
    return axios.get("http://localhost:8092/sample/api/employee/download/employees.xlsx?key="+query);
  }else{
    return axios.get("http://localhost:8092/sample/api/employee/download/employees.xlsx");
  }
 
}

