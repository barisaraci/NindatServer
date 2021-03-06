package packets;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;

public abstract class Packet implements Serializable {
	
	private static final long serialVersionUID = 7726201766814198940L;
	public byte type = 0;

	public byte getType() {
		return type;
	}

	public byte[] toByteArray() {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutput out = null;
		try {
			out = new ObjectOutputStream(bos);
			out.writeObject(this);
			byte[] bytes = bos.toByteArray();
			return bytes;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
				bos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static Packet fromByteArray(byte[] arr) {
		ByteArrayInputStream bis = new ByteArrayInputStream(arr);
		ObjectInput in = null;
		try {
			in = new ObjectInputStream(bis);
			Object o = in.readObject();
			return (Packet) o;
		} catch (IOException e) {
			if (e instanceof StreamCorruptedException) {
				System.out.println("Caught corrupted packet.");
				return null;
			}
			if (e instanceof EOFException) {
				System.out.println("eofexception : \n" + arr);
				return null;
			}
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				bis.close();
				if (in != null)
					in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return null;
	}

}