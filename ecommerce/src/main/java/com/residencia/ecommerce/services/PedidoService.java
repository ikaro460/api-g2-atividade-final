package com.residencia.ecommerce.services;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.residencia.ecommerce.dto.RelatorioPedidoDTO;
import com.residencia.ecommerce.entities.ItemPedido;
import com.residencia.ecommerce.entities.Pedido;
import com.residencia.ecommerce.repositories.PedidoRepository;

@Service
public class PedidoService {

	@Autowired
	PedidoRepository pedidoRepo;
	
	private ModelMapper modelMapper = new ModelMapper();

	public List<Pedido> listarPedidos() {
		return pedidoRepo.findAll();
	}

	public Pedido getPedidoPorId(Long id) {
		return pedidoRepo.findById(id).orElse(null);
	}

	public Pedido salvarPedido(Pedido pedido) {
		return pedidoRepo.save(pedido);
	}

	public Pedido atualizarPedido(Pedido pedido) {
		return pedidoRepo.save(pedido);
	}

	public boolean deletarPedido(Pedido pedido) {
		// verifica se pedido é valido
		if (pedido == null)
			return false;

		// verifica se existe no banco
		Pedido pedidoExistente = getPedidoPorId(pedido.getIdPedido());
		if (pedidoExistente == null)
			return false;

		// deleta pedido
		pedidoRepo.delete(pedido);

		// verifica se foi deletado de fato
		Pedido pedidoContinuaExistindo = getPedidoPorId(pedido.getIdPedido());
		if (pedidoContinuaExistindo == null)
			return true;
		return false;
	}
	
	private RelatorioPedidoDTO entityToDto(Pedido pedido) {
		RelatorioPedidoDTO relatorioPedidoDTO = modelMapper.map(pedido, RelatorioPedidoDTO.class);
		
		List<ItemPedido> itensPedidos = new ArrayList<>();
		
		for(ItemPedido itemPedido : pedido.getItemPedidos()) {
			itensPedidos.add(itemPedido);
		}
	
		relatorioPedidoDTO.setDataPedido(pedido.getDataPedido());
		relatorioPedidoDTO.setIdPedido(pedido.getIdPedido());
		relatorioPedidoDTO.setItemPedidos(itensPedidos);
		return relatorioPedidoDTO;
	}
	
	private Pedido DtoToEntity(RelatorioPedidoDTO relatorioPedidoDTO) {
		Pedido pedido = modelMapper.map(relatorioPedidoDTO, Pedido.class);
		return pedido;
	}
	
	public RelatorioPedidoDTO criarRelatorioDTO(RelatorioPedidoDTO pedidoDTO) {
		Pedido pedido = DtoToEntity(pedidoDTO);
		return entityToDto(pedidoRepo.save(pedido));
	}

}
