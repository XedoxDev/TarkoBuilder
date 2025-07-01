package org.xedox.demo;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class OutputView extends TextView {
    private final OutputStream outputStream;
    private final PrintStream printStream;

    public OutputView(Context context) {
        this(context, null);
    }

    public OutputView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OutputView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.outputStream = new TextViewOutputStream(this);
        this.printStream = new PrintStream(outputStream, true);
        setTextIsSelectable(true);
        setContextClickable(true);
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public PrintStream getPrintStream() {
        return printStream;
    }

    public void clear() {
        post(() -> setText(""));
    }

    private static class TextViewOutputStream extends OutputStream {
        private final TextView textView;
        private final StringBuilder buffer = new StringBuilder(1024);

        public TextViewOutputStream(TextView textView) {
            this.textView = textView;
        }

        @Override
        public void write(int b) throws IOException {
            synchronized (buffer) {
                buffer.append((char) b);
                if (b == '\n') {
                    flushBuffer();
                }
            }
        }

        @Override
        public void write(byte[] b, int off, int len) throws IOException {
            synchronized (buffer) {
                for (int i = off; i < off + len; i++) {
                    write(b[i]);
                }
            }
        }

        private void flushBuffer() {
            final String text;
            synchronized (buffer) {
                text = buffer.toString();
                buffer.setLength(0);
            }
            textView.post(
                    () -> {
                        textView.append(text);
                        if (textView.getLayout() != null) {
                            int scrollAmount =
                                    textView.getLayout().getLineTop(textView.getLineCount())
                                            - textView.getHeight();
                            if (scrollAmount > 0) {
                                textView.scrollTo(0, scrollAmount);
                            }
                        }
                    });
        }

        @Override
        public void flush() throws IOException {
            if (buffer.length() > 0) {
                flushBuffer();
            }
        }
    }
}
