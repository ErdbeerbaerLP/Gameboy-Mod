package eu.rekawek.coffeegb.serial;

public interface SerialEndpoint {

    int transfer(int outgoing);

    SerialEndpoint NULL_ENDPOINT = outgoing -> 0;
}
