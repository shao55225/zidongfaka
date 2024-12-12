package com.xunpay.money.utils;

import java.io.StringReader;
import java.io.Writer;

import org.mozilla.javascript.ErrorReporter;
import org.mozilla.javascript.EvaluatorException;

import com.yahoo.platform.yui.compressor.CssCompressor;
import com.yahoo.platform.yui.compressor.JavaScriptCompressor;

public class CompressorUtils {

	private static final boolean munge = true, verbose = false, preserveAllSemiColons = false, disableOptimizations = false;
	
	public static void javascript(String content, Writer writer) throws Exception {
		JavaScriptCompressor compressor = new JavaScriptCompressor(new StringReader(content), new ErrorReporter() {
			public void warning(String arg0, String arg1, int arg2, String arg3,
					int arg4) {
				
			}
			
			public EvaluatorException runtimeError(String arg0, String arg1, int arg2,
					String arg3, int arg4) {
				return null;
			}
			
			public void error(String arg0, String arg1, int arg2, String arg3, int arg4) {
			}
		});
		compressor.compress(writer, -1, munge, verbose, preserveAllSemiColons, disableOptimizations);
	}
	
	public static void css(String content, Writer writer) throws Exception {
		CssCompressor css = new CssCompressor(new StringReader(content));
		css.compress(writer, -1);		
	}
	
}
