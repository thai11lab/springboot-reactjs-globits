package com.globits.sample.rest;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.activation.MailcapCommandMap;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.compress.archivers.zip.PKWareExtraHeader.HashAlgorithm;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.globits.sample.domain.Employee;
import com.globits.sample.dto.EmployeeDto;
import com.globits.sample.dto.EmployeeSearchDTO;
import com.globits.sample.service.EmployeeService;

@RestController
@RequestMapping("/api/employee")
public class RestEmployeeController {

	@Autowired
	private EmployeeService employeeService;
	private FileInputStream fis;

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public List<EmployeeDto> getListAll() {
		List<EmployeeDto> dtos = employeeService.findAll();
		return dtos;
	}

	@RequestMapping(value = "/all1", method = RequestMethod.GET)
	public Page<EmployeeDto> getList(@RequestParam(value = "page") int page, @RequestParam(value = "size") int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<EmployeeDto> dtos = employeeService.findAll1(pageable);
		return dtos;
	}

	// Tim kiem theo dieu kien
	/*
	 * @RequestMapping(value = "/search/{keyword}/{page}/{size}", method =
	 * RequestMethod.GET) public Page<EmployeeDto>
	 * getListBySearch(@PathVariable("keyword") String
	 * keyString,@PathVariable("page") int page,@PathVariable("size") int size) {
	 * //,@RequestParam(value = "page") int page,@RequestParam(value = "size") int
	 * size //,@PathVariable("page") int page,@PathVariable("size") int size
	 * Pageable pageable = PageRequest.of(page, size); Page<EmployeeDto> dtos =
	 * employeeService.findBySearch(keyString, pageable); return dtos; }
	 */

	@RequestMapping(value = "/search/by", method = RequestMethod.GET)
	public Page<EmployeeDto> getListBySearch(@RequestParam Map<String, String> model) {
		// ,@RequestParam(value = "page") int page,@RequestParam(value = "size") int
		// size
		// ,@PathVariable("page") int page,@PathVariable("size") int size
		EmployeeSearchDTO employeeSearchDTO = new EmployeeSearchDTO();
		employeeSearchDTO.setKeyword(model.get("keyword"));
		Pageable pageable = PageRequest.of(Integer.parseInt(model.get("page")), Integer.parseInt(model.get("size")));
		Page<EmployeeDto> dtos = employeeService.findBySearch(employeeSearchDTO, pageable);
		return dtos;
	}

	@RequestMapping(value = "/search/{keyword}", method = RequestMethod.GET)
	public List<EmployeeDto> getListBySearch(@PathVariable("keyword") String keyString) {
		List<EmployeeDto> dtos = employeeService.findBySearch(keyString);
		return dtos;
	}

	@RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
	public EmployeeDto getList(@PathVariable("id") Long id) {
		EmployeeDto dtos = employeeService.findById(id);
		return dtos;
	}

	@RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
	public EmployeeDto update(@RequestBody Employee employee, @PathVariable("id") Long id) {
		EmployeeDto dto = new EmployeeDto();
		dto = employeeService.update(employee, id);
		return dto;
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public EmployeeDto add(@Validated @RequestBody Employee employee) {
		
		EmployeeDto employee2 = new EmployeeDto();
		EmployeeSearchDTO dto = new EmployeeSearchDTO();
		dto.setCode(employee.getCode());
		employee2= employeeService.insert(employee);

		return employee2;
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public EmployeeDto deleteOne(@PathVariable("id") Long id) {
		EmployeeDto employeeDto = employeeService.deleteOne(id);
		return employeeDto;
	}

	// Xuat file excell
	public static ByteArrayInputStream employeeToExcell(List<EmployeeDto> employees) throws Exception {

		try (Workbook workbook = new XSSFWorkbook();) {
			Sheet sheet = workbook.createSheet();
			Row row = sheet.createRow(0);
			CellStyle headerCellStyle = workbook.createCellStyle();
			headerCellStyle.setFillForegroundColor(IndexedColors.AQUA.getIndex());
			headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

			// Tao header
			Cell cell = row.createCell(0);
			cell.setCellValue("STT");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell(1);
			cell.setCellValue("Mã nhân viên");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell(2);
			cell.setCellValue("Họ và tên");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell(3);
			cell.setCellValue("Email");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell(4);
			cell.setCellValue("Số điện thoại");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell(5);
			cell.setCellValue("Tuổi");
			cell.setCellStyle(headerCellStyle);

			int i = 1;
			for (EmployeeDto item : employees) {
				Row dataRow = sheet.createRow(i + 1);
				dataRow.createCell(0).setCellValue(i++);
				dataRow.createCell(1).setCellValue(item.getCode());
				dataRow.createCell(2).setCellValue(item.getName());
				dataRow.createCell(3).setCellValue(item.getEmail());
				dataRow.createCell(4).setCellValue(item.getPhone());
				dataRow.createCell(5).setCellValue(item.getAge());
			}
			sheet.autoSizeColumn(0);
			sheet.autoSizeColumn(1);
			sheet.autoSizeColumn(2);
			sheet.autoSizeColumn(3);
			sheet.autoSizeColumn(4);
			sheet.autoSizeColumn(5);

			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			workbook.write(outputStream);
			return new ByteArrayInputStream(outputStream.toByteArray());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@RequestMapping(value = "/download/employees.xlsx", method = RequestMethod.GET)
	public void downloadExcell(HttpServletResponse response,
			@RequestParam(value = "key", defaultValue = "") String key) {
		List<EmployeeDto> employees = new ArrayList<>();
		if (StringUtils.isNotEmpty(key)) {
			employees = employeeService.findBySearch(key);
		} else {
			employees = employeeService.findAll();
		}

		response.setContentType("application/octet-stream");
		;
		response.setHeader("Content-Disposition", "attachment; filename=employee.xlsx");
		ByteArrayInputStream stream;
		try {
			stream = employeeToExcell(employees);
			IOUtils.copy(stream, response.getOutputStream());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
