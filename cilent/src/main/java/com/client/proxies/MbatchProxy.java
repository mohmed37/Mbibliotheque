package com.client.proxies;

        import com.client.bean.LibrairieBean;
        import org.springframework.cloud.netflix.ribbon.RibbonClient;
        import org.springframework.cloud.openfeign.FeignClient;
        import org.springframework.web.bind.annotation.GetMapping;

        import java.util.List;

@FeignClient(name="zuul-server")
@RibbonClient("microservice-mail")
public interface MbatchProxy {
        @GetMapping(value = "/microservice-mail/mail")
        void sendEmail();
}
