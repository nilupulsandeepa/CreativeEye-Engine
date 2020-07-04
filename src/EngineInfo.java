//Import LWJGL

import org.lwjgl.Version;

public class EngineInfo {

    String lwjglVersion;
    String cpuArchitecture;

    EngineInfo() {
        this.lwjglVersion = Version.getVersion();
        this.cpuArchitecture = System.getProperty("os.arch");
    }

    @Override
    public String toString() {
        return "Using LWGJW Version : " + this.lwjglVersion + "\n" + "CPU Architecture : " + this.cpuArchitecture;
    }
}
