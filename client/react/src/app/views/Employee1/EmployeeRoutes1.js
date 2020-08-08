import { EgretLoadable } from "egret";
import ConstantList from "../../appConfig";
const EmployeeTable1 = EgretLoadable({
  loader: () => import("./EmployeeTable1")
});

const employeeRoutes1 = [
  {
    path:  ConstantList.ROOT_PATH+"dashboard/nhanvien",
    exact: true,
    component: EmployeeTable1
  }
];

export default employeeRoutes1;
