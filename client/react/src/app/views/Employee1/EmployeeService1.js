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
    var pageIndex = page;
    var params= "page="+pageIndex+"&size="+rowsPerPage;
    return axios.get("http://localhost:8092/sample/api/employee/search?keyword="+query+"&"+params);
}

export const getAllEmployeesBySearchExcell = (query) =>{
  
  return axios.get("http://localhost:8092/sample/api/employee/search/"+query);
}

export const exportFileExcell1 = key =>{
  if(key!=" " && key != null){
    return axios.get("http://localhost:8092/sample/api/employee/download/employees.xlsx?key="+key);
  }else{
    return axios.get("http://localhost:8092/sample/api/employee/download/employees.xlsx");
  }
}

