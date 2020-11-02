package edu.drexel.se320;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import java.util.List;
import java.io.IOException;
import java.util.NoSuchElementException;

public class MockTests {

    public MockTests() {}

    /**
     * Demonstrate a working mock from the Mockito documentation.
     * https://static.javadoc.io/org.mockito/mockito-core/3.1.0/org/mockito/Mockito.html#1
     */
    @Test
    public void testMockDemo() {
         List mockedList = mock(List.class);

         mockedList.add("one");
         mockedList.clear();

         verify(mockedList).add("one");
         verify(mockedList).clear();
    }

    // Given test passing after the refactoring
    @Test
    public void testServerConnectionFailureGivesNull() throws IOException {
        Client c = new Client();
        ServerConnection sc = mock(ServerConnection.class);
        when(sc.connectTo(anyString())).thenReturn(false);
        c.createServerConnection(sc);
        // If you change the code to pass the mock above to the client (based on your choice of
        // refactoring), this test should pass.  Until then, it will fail.
        assertNull(c.requestFile("DUMMY", "DUMMY"));
    }

    // Test that if the attempt to connectTo(...)
    // the server fails, the client code calls no further methods on the connection.
    @Test
    public void testServerConnectTo() throws IOException{
        Client c = new Client();
        ServerConnection sc = mock(ServerConnection.class);
        when(sc.connectTo(anyString())).thenReturn(false);
        c.createServerConnection(sc);
        c.requestFile("DUMMY", "DUMMY");

        verify(sc, times(1)).connectTo("DUMMY");
        verify(sc,never()).requestFileContents("DUMMY");
        verify(sc,never()).read();
        verify(sc,never()).moreBytes();
        verify(sc, never()).closeConnection();
    }

    //Test that if the connection succeeds but the file name is invalid,
    // the client code calls no further methods on the connection except closeConnection.
    // That is, the client code is expected to call closeConnection,
    // but should not call other methods after it is known the file name is invalid.
    // This test fails because of the bug in the Client code
    // as close connection can only be called only in the if branch when file name is valid
    @Test
    public void testFileNameInValid() throws IOException{
        Client c = new Client();
        ServerConnection sc = mock(ServerConnection.class);
        when(sc.connectTo("DUMMY")).thenReturn(true);
        when(sc.requestFileContents(anyString())).thenReturn(false);
        c.createServerConnection(sc);
        c.requestFile("DUMMY", "DUMMY");

        verify(sc, times(1)).connectTo("DUMMY");
        verify(sc, times(1)).requestFileContents("DUMMY");
        verify(sc,never()).read();
        verify(sc,never()).moreBytes();
        verify(sc, times(1)).closeConnection();
    }

    //Test that if the connection succeeds and the file is valid and non-empty,
    // that the connection asks for at least some part of the file.
    // test verifies the read part of the file
    @Test
    public void testFileNameValidNonEmpty() throws IOException{
        Client c = new Client();
        ServerConnection sc = mock(ServerConnection.class);
        when(sc.connectTo("DUMMY")).thenReturn(true);
        when(sc.requestFileContents(anyString())).thenReturn(true);
        when(sc.moreBytes()).thenReturn(true, false);
        when(sc.read()).thenReturn("DUMMY TEXT RETURNED NOT FINISHED YET");
        c.createServerConnection(sc);
        String result  = c.requestFile("DUMMY", "DUMMY");

        verify(sc, times(1)).connectTo("DUMMY");
        verify(sc, times(1)).requestFileContents("DUMMY");
        verify(sc,atLeast(1)).read();
        verify(sc,atLeast(1)).moreBytes();
        verify(sc, times(1)).closeConnection();
        assertEquals(result, "DUMMY TEXT RETURNED NOT FINISHED YET");
    }

    // Test that if the connection succeeds and the file is valid but empty,
    // that the client returns an empty string.
    @Test
    public void testFileNameValidEmpty() throws IOException{
        Client c = new Client();
        ServerConnection sc = mock(ServerConnection.class);
        when(sc.connectTo("DUMMY")).thenReturn(true);
        when(sc.requestFileContents(anyString())).thenReturn(true);
        when(sc.moreBytes()).thenReturn(false);
        c.createServerConnection(sc);
        String result  = c.requestFile("DUMMY", "DUMMY");

        verify(sc, times(1)).connectTo("DUMMY");
        verify(sc, times(1)).requestFileContents("DUMMY");
        verify(sc,never()).read();
        verify(sc,times(1)).moreBytes();
        verify(sc, times(1)).closeConnection();
        assertEquals(result, "");

    }

    // Test that if the client successfully reads part of a file,
    // and then an IOException occurs before the file is fully read (i.e., moreBytes() has not returned false),
    // the client still returns null to indicate an error, rather than returning a partial result.
    @Test
    public void testFileNamePartialRead() throws IOException{
        Client c = new Client();
        ServerConnection sc = mock(ServerConnection.class);
        when(sc.connectTo("DUMMY")).thenReturn(true);
        when(sc.requestFileContents(anyString())).thenReturn(true);
        when(sc.moreBytes()).thenReturn(true,true);
        when(sc.read()).thenReturn("DUMMY TEXT").thenThrow(new IOException());
        c.createServerConnection(sc);
        String result  = c.requestFile("DUMMY", "DUMMY");

        verify(sc, times(1)).connectTo("DUMMY");
        verify(sc, times(1)).requestFileContents("DUMMY");
        verify(sc,atLeast(1)).read();
        verify(sc,atLeast(1)).moreBytes();
        verify(sc, never()).closeConnection();

        assertNull(result);
    }

    // Test that if the initial server connection succeeds,
    // then if a IOException occurs while retrieving the file (requesting, or reading bytes, either one)
    // the client still explicitly closes the server connection
    // made two different Server Connection objects to test either cases
    // This test fails no call to close connection
    // because of the IO Exception occurred before the close connection
    @Test
    public void testServer() throws IOException{
        Client c = new Client();
        ServerConnection sc1 = mock(ServerConnection.class);
        when(sc1.connectTo("DUMMY")).thenReturn(true);
        when(sc1.requestFileContents(anyString())).thenReturn(true);
        when(sc1.moreBytes()).thenThrow(new IOException());
        c.createServerConnection(sc1);
        String result  = c.requestFile("DUMMY", "DUMMY");

        verify(sc1, times(1)).connectTo("DUMMY");
        verify(sc1, times(1)).requestFileContents("DUMMY");
        verify(sc1,never()).read();
        verify(sc1,times(1)).moreBytes();
        verify(sc1, times(1)).closeConnection();

        assertNull(result);

        ServerConnection sc2 = mock(ServerConnection.class);
        when(sc2.connectTo("DUMMY")).thenReturn(true);
        when(sc2.requestFileContents(anyString())).thenThrow(new IOException());
        when(sc2.moreBytes()).thenReturn(true);
        c.createServerConnection(sc2);
        result  = c.requestFile("DUMMY", "DUMMY");

        verify(sc2, times(1)).connectTo("DUMMY");
        verify(sc2, times(1)).requestFileContents("DUMMY");
        verify(sc2,never()).read();
        verify(sc2,never()).moreBytes();
        verify(sc2, times(1)).closeConnection();

        assertNull(result);
    }

    // Test that the client simply returns unmodified the contents
    // if it reads a file from the server starting with “override”,
    // i.e., it does not interpret a prefix of “override” as a trigger for some weird other behavior.
    @Test
    public void testFileOverride() throws IOException{
        Client c = new Client();
        ServerConnection sc = mock(ServerConnection.class);
        when(sc.connectTo("DUMMY")).thenReturn(true);
        when(sc.requestFileContents(anyString())).thenReturn(true);
        when(sc.moreBytes()).thenReturn(true, false);
        when(sc.read()).thenReturn("override dummy text");
        c.createServerConnection(sc);
        String result  = c.requestFile("DUMMY", "DUMMY");

        verify(sc, times(1)).connectTo("DUMMY");
        verify(sc, times(1)).requestFileContents("DUMMY");
        verify(sc,atLeast(1)).read();
        verify(sc,atLeast(1)).moreBytes();
        verify(sc, times(1)).closeConnection();
        assertEquals(result, "override dummy text");
    }

    // If the server returns the file in three pieces (i.e., two calls to read() must be executed),
    // the client concatenates them in the correct order).
    @Test
    public void testReadThreePieces() throws IOException{
        Client c = new Client();
        ServerConnection sc = mock(ServerConnection.class);
        when(sc.connectTo("DUMMY")).thenReturn(true);
        when(sc.requestFileContents(anyString())).thenReturn(true);
        when(sc.moreBytes()).thenReturn(true, true, true, false);
        when(sc.read()).thenReturn("first ").thenReturn("second ").thenReturn("third");
        c.createServerConnection(sc);
        String result  = c.requestFile("DUMMY", "DUMMY");

        verify(sc, times(1)).connectTo("DUMMY");
        verify(sc, times(1)).requestFileContents("DUMMY");
        verify(sc,times(3)).read();
        verify(sc,atLeast(1)).moreBytes();
        verify(sc, times(1)).closeConnection();
        assertEquals(result, "first second third");
    }

    // If read() ever returns null,
    // the client treats this as the empty string
    // (as opposed to appending “null” to the file contents read thus far, which is the default if you simply append null).
    @Test
    public void testReadReturnsNull() throws IOException{
        Client c = new Client();
        ServerConnection sc = mock(ServerConnection.class);
        when(sc.connectTo("DUMMY")).thenReturn(true);
        when(sc.requestFileContents(anyString())).thenReturn(true);
        when(sc.moreBytes()).thenReturn(true, true, false);
        when(sc.read()).thenReturn("previous text", null);
        c.createServerConnection(sc);
        String result  = c.requestFile("DUMMY", "DUMMY");

        verify(sc, times(1)).connectTo("DUMMY");
        verify(sc, times(1)).requestFileContents("DUMMY");
        verify(sc,atLeast(1)).read();
        verify(sc,atLeast(1)).moreBytes();
        verify(sc, times(1)).closeConnection();
        assertEquals(result, "previous text");

    }

    // Test that if connectTo operation fails the first time it is executed with an IOException, the client returns null.
    @Test
    public void testConnectToFail() throws IOException{
        Client c = new Client();
        ServerConnection sc = mock(ServerConnection.class);
        when(sc.connectTo("DUMMY")).thenThrow(new IOException());
        when(sc.requestFileContents(anyString())).thenReturn(true);
        when(sc.moreBytes()).thenReturn(true, false);
        when(sc.read()).thenReturn("previous text");
        c.createServerConnection(sc);
        String result  = c.requestFile("DUMMY", "DUMMY");

        verify(sc, times(1)).connectTo("DUMMY");
        verify(sc, never()).requestFileContents("DUMMY");
        verify(sc, never()).read();
        verify(sc, never()).moreBytes();
        verify(sc, never()).closeConnection();
        assertNull(result);

    }

    // Test that if requestFileContents operation fails the first time it is executed with an IOException, the client returns null.
    @Test
    public void testRequestFileContentsFail() throws IOException{
        Client c = new Client();
        ServerConnection sc = mock(ServerConnection.class);
        when(sc.connectTo("DUMMY")).thenReturn(true);
        when(sc.requestFileContents(anyString())).thenThrow(new IOException());
        when(sc.moreBytes()).thenReturn(true, false);
        when(sc.read()).thenReturn("previous text");
        c.createServerConnection(sc);
        String result  = c.requestFile("DUMMY", "DUMMY");

        verify(sc, times(1)).connectTo("DUMMY");
        verify(sc, times(1)).requestFileContents("DUMMY");
        verify(sc, never()).read();
        verify(sc, never()).moreBytes();
        verify(sc, never()).closeConnection();
        assertNull(result);

    }

    // Test that if moreBytes operation fails the first time it is executed with an IOException, the client returns null.
    @Test
    public void testMoreBytesFail() throws IOException{
        Client c = new Client();
        ServerConnection sc = mock(ServerConnection.class);
        when(sc.connectTo("DUMMY")).thenReturn(true);
        when(sc.requestFileContents(anyString())).thenReturn(true);
        when(sc.moreBytes()).thenThrow(new IOException());
        when(sc.read()).thenReturn("previous text");
        c.createServerConnection(sc);
        String result  = c.requestFile("DUMMY", "DUMMY");

        verify(sc, times(1)).connectTo("DUMMY");
        verify(sc, times(1)).requestFileContents("DUMMY");
        verify(sc, never()).read();
        verify(sc, times(1)).moreBytes();
        verify(sc, never()).closeConnection();
        assertNull(result);

    }

    // Test that if read operation fails the first time it is executed with an IOException, the client returns null.
    @Test
    public void testReadFail() throws IOException{
        Client c = new Client();
        ServerConnection sc = mock(ServerConnection.class);
        when(sc.connectTo("DUMMY")).thenReturn(true);
        when(sc.requestFileContents(anyString())).thenReturn(true);
        when(sc.moreBytes()).thenReturn(true);
        when(sc.read()).thenThrow(new IOException());
        c.createServerConnection(sc);
        String result  = c.requestFile("DUMMY", "DUMMY");

        verify(sc, times(1)).connectTo("DUMMY");
        verify(sc, times(1)).requestFileContents("DUMMY");
        verify(sc, times(1)).read();
        verify(sc, times(1)).moreBytes();
        verify(sc, never()).closeConnection();
        assertNull(result);

    }

    // Test that if closeConnection operation fails the first time it is executed with an IOException, the client returns null.
    @Test
    public void testCloseConnectionFail() throws IOException{
        Client c = new Client();
        ServerConnection sc = mock(ServerConnection.class);
        when(sc.connectTo("DUMMY")).thenReturn(true);
        when(sc.requestFileContents(anyString())).thenReturn(true);
        when(sc.moreBytes()).thenReturn(true, false);
        when(sc.read()).thenReturn("New Text");
        doThrow(new IOException()).when(sc).closeConnection();
        c.createServerConnection(sc);
        String result  = c.requestFile("DUMMY", "DUMMY");

        verify(sc, times(1)).connectTo("DUMMY");
        verify(sc, times(1)).requestFileContents("DUMMY");
        verify(sc, times(1)).read();
        verify(sc, atLeast(1)).moreBytes();
        verify(sc, times(1)).closeConnection();
        assertNull(result);
    }
}
