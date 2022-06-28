/*
 * Copyright 2012-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.boot.docs.web.servlet.springmvc.errorhandling

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import javax.servlet.RequestDispatcher
import javax.servlet.http.HttpServletRequest

@ControllerAdvice(basePackageClasses = [SomeController::class])
class MyControllerAdvice : ResponseEntityExceptionHandler() {

	@ResponseBody
	@ExceptionHandler(MyException::class)
	fun handleControllerException(request: HttpServletRequest, ex: Throwable): ResponseEntity<*> {
		val status = getStatus(request)
		return ResponseEntity(MyErrorBody(status.value(), ex.message), status)
	}

	private fun getStatus(request: HttpServletRequest): HttpStatus {
		val code = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE) as Int
		val status = HttpStatus.resolve(code)
		return status ?: HttpStatus.INTERNAL_SERVER_ERROR
	}

}
