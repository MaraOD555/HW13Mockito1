package pro.sky.HW13Mockito.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INSUFFICIENT_STORAGE, reason = "Переполнение массива")
public class EmployeeStorageIsFullException extends RuntimeException{
}
