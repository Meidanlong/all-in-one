/**
 * 
 */
package com.meidl.mongodb.service;


import com.meidl.mongodb.domain.File;

import java.util.List;
import java.util.Optional;

/**
 * File 服务接口.
 */
public interface FileService {
	/**
	 * 保存文件
	 * @return
	 */
	File saveFile(File file);
	
	/**
	 * 删除文件
	 * @return
	 */
	void removeFile(String id);
	
	/**
	 * 根据id获取文件
	 * @return
	 */
	Optional<File> getFileById(String id);

	/**
	 * 分页查询，按上传时间降序
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	List<File> listFilesByPage(int pageIndex, int pageSize);
}
