package api.dto;

public class ContainerDto<T> {
    private int itemPerPage;
    private int page;
    private int total;
    private T[] model;

    public ContainerDto(int itemPerPage, int page, int total, T[] model) {
        this.itemPerPage = itemPerPage;
        this.page = page;
        this.total = total;
        this.model = model;
    }

    public T[] getModel() {
        return model;
    }

    public boolean isValidPage(int p) {
        return p > 0 && p < (total / itemPerPage + 0.5);
    }
}